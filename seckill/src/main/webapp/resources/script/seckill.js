//存放主要交付逻辑
var seckill = {
    //封装秒杀相关ajax的url
    URL:{
        now : function () {
            return '/seckill/time/now';
        },
        exposer : function (seckillId) {
            return '/seckill/'+seckillId+'/exposer';
        },
        execution:function (seckillId,md5) {
            return "/seckill/"+seckillId+"/"+md5+"/execution";
        }
    },
    //验证手机号
    validatePhone:function (phone) {
        if(phone && phone.length == 11 && !isNaN(phone)){
            return true;
        }else{
            return false;
        }
    },
    handleSeckillkill:function (sckillId,node) {
        //获取秒杀地址，控制实现逻辑，执行秒杀
        node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>')
        $.post(seckill.URL.exposer(sckillId),function (result) {
            //在回调函数中，执行交互流程
            if(result && result['success']){
                var exposer = result['data'];
                if(exposer['exposed']){ //是否开启秒杀
                    //获取秒杀地址
                    var md5 = exposer['md5'];
                    var killUrl = seckill.URL.execution(sckillId,md5);
                    //绑定一次点击事件
                    $("#killBtn").one("click",function () {
                        $(this).addClass('disabled'); //禁用按钮
                        //发送请求,执行秒杀请求
                        $.post(killUrl,function (result) {
                            if(result && result['success']){
                                var killResult = result['data'];
                                var state = killResult['state'];
                                var atateInfo = killResult['atateInfo'];
                                //显示秒杀结果
                                node.html('<span class="label label-success">'+atateInfo+'</span>')
                            }
                        });
                    });
                    node.show();
                }else{
                    //未开启秒杀
                    var now = exposer['now'];
                    var start = exposer['start'];
                    var end = exposer['end'];
                    //重新计算倒计时
                    seckill.countdown(sckillId,now,start,end);
                }
            }else {
                console.log('result='+result);
            }
        });
    },
    countdown:function (sckillId,nowTime,startTime,endTime) {
        var seckillBox = $("#seckill-box");
        //时间判断
        if(nowTime > endTime){
            //秒杀结束
            seckillBox.html("秒杀结束");
        }else if (nowTime < startTime){
            //秒杀未开始,计时绑定
            var killTime = new Date(startTime + 1000);
            seckillBox.countdown(killTime,function (event) {
                //时间格式
                var format = event.strftime("秒杀倒计时：%D天 %H时 %M分 %S秒");
                seckillBox.html(format);
            }).on("finish.countdown",function () {  //时间完成回调函数
                seckill.handleSeckillkill(sckillId,seckillBox);
            });
        }else{
            //秒杀开始
            seckill.handleSeckillkill(sckillId,seckillBox);
        }
    },
    detail:{
        //详情页初始化
        init:function (params) {
            //手机验证和登录，计时交互
            //在cookie 中查找手机号
            var killPhone = $.cookie('killPhone');
            //验证手机号
            if(!seckill.validatePhone(killPhone)){
                //绑定手机号
                //控制输出
                var killPhoneModal = $("#killPhoneModal");
                //显示弹出层
                killPhoneModal.modal({
                    show:true,//显示弹出
                    backdrop:'static', //禁止位置关闭
                    keyboard:false //关闭键盘事件
                });
                $("#killPhoneBtn").click(function () {
                    var inputPhone = $("#killPhoneKey").val();
                    if (seckill.validatePhone(inputPhone)){
                        //电话写入cookie 和过期时间，和生效路径
                        $.cookie("killPhone",inputPhone,{expires:7,path:'/seckill'});
                        //刷新页面
                        window.location.reload();
                    }else {
                        $("#killPhoneMessage").hide().html("<label class='label label-danger'>手机号码错误!</label>").show(300);
                    }
                });
            }
            //已经登录了
            //计时交付
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var seckillId = params['seckillId'];
            $.get(seckill.URL.now(),function (result) {
                if(result && result['success']){
                    var nowTime = result['data'];
                    seckill.countdown(seckillId,nowTime,startTime,endTime);
                }else {
                    alert("获取时间失败");
                    console.log("result"+result);
                }
            });
        }
    }
};