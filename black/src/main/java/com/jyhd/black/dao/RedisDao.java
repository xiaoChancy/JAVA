package com.jyhd.black.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Repository
public class RedisDao<T> {

    @Autowired
    protected RedisTemplate<String, Object> redisTemplate;

    @Resource
    protected HashOperations<String, String, T> hashOperations;

    @Resource
    protected ValueOperations<String,Object> stringValue;

    @Resource
    protected ListOperations<String,Object> listOperations;

    @Resource
    protected SetOperations<String,Object> setOperations;

    /******************************************String************************************************/

    /**
     * 查询
     * @param key
     * @return
     */
    public String get(String key){
        return redisTemplate.boundValueOps(key).get(0,-1);
    }

    /**
     * 添加
     * @param key key
     * @param doamin 对象
     */
    public void set(String key,Object doamin){
        stringValue.set(key,doamin);
    }
    /**
     * 添加
     * @param key key
     * @param doamin 对象
     * @param expire 过期时间(单位:秒)
     */
    public void set(String key,Object doamin,long expire){
        stringValue.set(key,doamin);
        if (expire > 0) {
            stringValue.set(key,doamin,expire,TimeUnit.SECONDS);
        }
    }

    /**
     * 删除
     * @param key
     */
    public void del(String key){
        redisTemplate.delete(key);
    }

    /**
     *
     */
    public Long incr(String key,Long number){
        return stringValue.increment(key ,number);
    }

    /******************************************String************************************************/


    /******************************************Set************************************************/
    /**
     * 添加
     * @param key
     * @param value
     * @return
     */
    public Long sadd(String key,Object value){
        return setOperations.add(key,value);
    }

    /**
     * 删除指定值
     * @param key
     * @param value
     * @return
     */
    public Long sremove(String key,Object value){
        return setOperations.remove(key,value);
    }

    /**
     * 删除全部
     * @param key
     * @return
     */
    public Long sremoveAll(String key){
        return setOperations.remove(key);
    }

    /**
     * 移动到另一个集合
     * @param key1 要移动的key
     * @param value 要移动的值
     * @param key2  移动的目的地的key
     * @return
     */
    public boolean smove(String key1,Object value,String key2){
        return setOperations.move(key1,value,key2);
    }

    /**
     *  返回元素个数
     * @param key
     * @return
     */
    public Long ssize(String key){
        return setOperations.size(key);
    }

    /**
     * 返回所有集合
     * @param key
     * @return
     */
    public Set<Object> sgetAll(String key){
        return setOperations.members(key);
    }

    /**
     * 返回一个随机集合
     * @param key
     * @return
     */
    public Object srandmember(String key){
        return setOperations.randomMember(key);
    }

    /**
     *  弹出首元素
     * @param key
     * @return
     */
    public Object spop(String key){
        return setOperations.pop(key);
    }

    /******************************************Set************************************************/


    /******************************************List************************************************/
    /**
     * 从右边插入
     * @param name
     * @param domian
     * @return
     */
    public Long rpush(String name , Object domian){
        return listOperations.rightPush(name,domian);
    }

    /**
     * 从左边插入
     * @param name
     * @param domian
     * @return
     */
    public Long lpush(String name , Object domian){
        return listOperations.leftPush(name,domian);
    }

    /**
     * 列表长度
     * @param name
     * @return
     */
    public Long llen(String name){
        return listOperations.size(name);
    }

    /**
     * 返回左边元素
     * @param name
     * @return
     */
    public Object lpop(String name){
        return listOperations.leftPop(name);
    }

    /**
     * 返回右边元素
     * @param name
     * @return
     */
    public Object rpop(String name){
        return listOperations.rightPop(name);
    }

    /******************************************List************************************************/


    /******************************************Hash**************************************************/
    /**
     * 添加
     * @param key    key
     * @param doamin 对象
     */
    public void hput(String name,String key, T doamin) {
        hashOperations.put(name, key, doamin);
    }

    /**
     * 添加
     * @param key    key
     * @param doamin 对象
     * @param expire 过期时间(单位:秒)
     */
    public void hput(String name,String key, T doamin, long expire) {
        hashOperations.put(name, key, doamin);
        if (expire > 0) {
            redisTemplate.expire(name, expire, TimeUnit.SECONDS);
        }
    }
    /**
     * 删除
     * @param key 传入key的名称
     */
    public void hremove(String name,String key) {
        hashOperations.delete(name, key);
    }

    /**
     * 查询
     * @param key 查询的key
     * @return
     */
    public T hget(String name,String key) {
        return hashOperations.get(name, key);
    }

    /**
     * 获取当前redis库下所有对象
     * @return
     */
    public List<T> hgetAll(String name) {
        return hashOperations.values(name);
    }

    /**
     * 查询查询当前redis库下所有key
     *
     * @return
     */
    public Set<String> hgetKeys(String name) {
        return hashOperations.keys(name);
    }

    /**
     * 判断key是否存在redis中
     *
     * @param key 传入key的名称
     * @return
     */
    public boolean hisKeyExists(String name,String key) {
        return hashOperations.hasKey(name, key);
    }

    /**
     * 查询当前key下缓存数量
     *
     * @return
     */
    public long hcount(String name) {
        return hashOperations.size(name);
    }

    /**
     * 清空redis
     */
    public void hempty(String name) {
        Set<String> set = hashOperations.keys(name);
        set.stream().forEach(key -> hashOperations.delete(name, key));
    }
    /******************************************Hash**************************************************/


}
