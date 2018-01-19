package org.seckill.seckill.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.seckill.seckill.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 通过Jedis接口使用Redis数据库
 *
 * 注意：往Redis中放的对象一定要序列化之后再放入，参考http://www.cnblogs.com/yaobolove/p/5632891.html
 * 序列化的目的是将一个实现了Serializable接口的对象转换成一个字节序列,可以。 把该字节序列保存起来(例如:保存在一个文件里),
 * 以后可以随时将该字节序列恢复为原来的对象。
 *
 * Redis 缓存对象时需要将其序列化，而何为序列化，实际上就是将对象以字节形式存储。这样，不管对象的属性是字符串、整型还是图片、视频等二进制类型，
 * 都可以将其保存在字节数组中。对象序列化后便可以持久化保存或网络传输。需要还原对象时，只需将字节数组再反序列化即可。
 *
 * 因为要在项目中用到，所以要添加@Service，把这个做成一个服务
 *
 * 因为要初始化连接池JedisPool，所以要implements InitializingBean并调用默认的
 * afterPropertiesSet()方法
 *
 */

@Service
public class RedisDao implements InitializingBean {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // 连接池
    private JedisPool jedisPool;

    // protostuff序列化工具用到的架构
    // 对于对象，可以用RuntimeSchema来生成schema(架构)通过反射在运行时缓存和使用
    private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);

    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub
        // 初始化连接池,连接Redis中第0个数据库，Redis端口是6379，默认配置Redis最多有16个数据库
        jedisPool = new JedisPool("redis://localhost:6379/0");

    }

    public Seckill getSeckill(long seckillId){
        try {
            Jedis jedis  = jedisPool.getResource();
            try {
                String key = "seckill:"+seckillId;
                //并没有实现内部序列化操作
                //采用自定义序列化
                //protostuff ： pojo.
                byte[] bytes = jedis.get(key.getBytes());
                //缓存重获取到
                if(bytes != null){
                    //空对象
                    Seckill seckill = schema.newMessage();
                    ProtostuffIOUtil.mergeFrom(bytes,seckill,schema);
                    //seckill 被反序列化
                    return  seckill;
                }
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }

        return null;
    }

    public String putSeckill(Seckill seckill){
        //set Object(Seckill)-> 序列化 -> byte[]
        //redis操作逻辑
        try {
            Jedis jedis  = jedisPool.getResource();
            try {
                String key ="seckill:" + seckill.getSeckillId();
                byte[] bytes = ProtostuffIOUtil.toByteArray(seckill,schema,
                        LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                int timeOut = 60 * 60;
                //超时缓存
                return jedis.setex(key.getBytes(),timeOut,bytes);
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }

}
