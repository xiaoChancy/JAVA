function backIndex() {
    window.document.onkeypress = function (keyEvent) {
        keyEvent = keyEvent ? keyEvent : window.event;
        keyEvent.which = keyEvent.which ? keyEvent.which : keyEvent.keyCode;
        console.log(keyEvent.which);
        if (keyEvent.which == 8) {
            gotoIndex();
        }
    }
}
function animate(obj,styleJson,time,callback){
    clearInterval(obj.timer);
    // 开启定时器
    obj.timer=setInterval(function(){
        var flag=true;//假设所有动作都已完成成立。
        for(var styleName in styleJson){
            //1.取当前属性值
            var iMov=0;
            // 透明度是小数，所以得单独处理
            iMov=styleName=='opacity'?Math.round(parseFloat(getStyle(obj,styleName))*100):parseInt(getStyle(obj,styleName));

            //2.计算速度
            var speed=0;
            speed=(styleJson[styleName]-iMov)/120;//缓冲处理，这边也可以是固定值
            speed=speed>0?Math.ceil(speed):Math.floor(speed);//区分透明度及小数点，向上取整，向下取整

            //3.判断是否到达预定值
            if(styleJson[styleName]!=iMov){
                flag=false;
                if(styleName=='opacity'){//判断结果是否为透明度
                    obj.style[styleName]=(iMov+speed)/100;
                    obj.style.filter='alpha(opacity:'+(iMov+speed)+')';
                }else{
                    obj.style[styleName]=iMov+speed+'px';
                }
            }
        }
        if(flag){//到达设定值，停止定时器，执行回调
            clearInterval(obj.timer);
            if(callback){callback();}
        }
    },time)
}

//返回id
// function $(id) {
//     return document.getElementById(id);
// }

//获取对象样式规则信息，IE下使用currentStyle
function getStyle(obj,style){
    return obj.currentStyle?obj.currentStyle[style]:getComputedStyle(obj,false)[style];
}

//判断class是否存在
function hasClass(ele, cls) {
    return ele.className.match(new RegExp("(\\s|^)" + cls + "(\\s|$)"));
}
//为指定的dom元素添加样式
function addClass(ele, cls) {
    if (!hasClass(ele, cls)){
        ele.className += " " + cls;
    }
}
//删除指定dom元素的样式
function removeClass(ele, cls) {
    if (hasClass(ele, cls)) {
        var reg = new RegExp("(\\s|^)" + cls + "(\\s|$)");
        ele.className = ele.className.replace(reg, "");
    }
}
//如果存在(不存在)，就删除(添加)一个样式
function toggleClass(ele,cls){
    if(hasClass(ele,cls)){
        removeClass(ele, cls);
    }else{
        addClass(ele, cls);
    }
}

function prizeRand(oArr) {
    var sum = 0;    // 总和
    var rand = 0;   // 每次循环产生的随机数
    var result = 0; // 返回的对象的key
    // 计算总和
    for (var i in oArr) {
        sum += oArr[i];
    }
    // 如果设置的数落在随机数内，则返回，否则减去本次的数
    for (var i in oArr) {
        rand = Math.floor(Math.random() * sum + 1);
        if (oArr[i] >= rand) {
            result = i;
            break;
        } else {
            sum -= oArr[i];
        }
    }
    oArr = undefined;
    return result;
}
function get_cookie(Name) {
    var search = Name + "=";
    var returnvalue = "";
    if (document.cookie.length > 0) {
        offset = document.cookie.indexOf(search);
        if (offset != -1) {
            // if cookie exists
            offset += search.length;
            // set index of beginning of value
            end = document.cookie.indexOf(";", offset);
            // set index of end of cookie value
            if (end == -1)
                end = document.cookie.length;
            returnvalue=decodeURI(document.cookie.substring(offset, end))
        }
    }
    return returnvalue;
}