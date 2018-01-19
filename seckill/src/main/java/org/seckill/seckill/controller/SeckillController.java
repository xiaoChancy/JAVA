package org.seckill.seckill.controller;


import org.seckill.seckill.dto.Exposer;
import org.seckill.seckill.dto.SeckillExecution;
import org.seckill.seckill.dto.SeckillResult;
import org.seckill.seckill.entity.Seckill;
import org.seckill.seckill.enums.SeckillStatEnum;
import org.seckill.seckill.exception.RepeatKillExecption;
import org.seckill.seckill.exception.SeckillCloseExecption;
import org.seckill.seckill.exception.SeckillException;
import org.seckill.seckill.service.SeckillService;
import org.seckill.seckill.service.impl.SeckillServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
@Controller
@RequestMapping("/seckill")
public class SeckillController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillServiceImpl seckillService;

    /**
     * 获取列表页
     * @param model
     * @return
     */
    @GetMapping("/list")
    public String list(Model model){
        List<Seckill> seckill = seckillService.getSeckillList();
        model.addAttribute("list",seckill);
        return "list";
    }

    /**
     * 查询详情
     * @param secillId
     * @param model
     * @return
     */
    @GetMapping(value = "/{seckillId}/detail")
    public String detail(@PathVariable("seckillId") Long secillId ,Model model){
        if(secillId == null){
            return "redirect:/seckill/list";
        }
        Seckill seckill = seckillService.getById(secillId);
        if(seckill == null){
            return "forward:seckill/list";
        }
        model.addAttribute("seckill",seckill);
        return "detail";
    }

    /**
     * 暴露秒杀接口地址
     * @param seckillId
     * @return
     */
    @PostMapping(value = "/{seckillId}/exposer",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId){
        SeckillResult<Exposer> result;
        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(true ,exposer);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            result = new SeckillResult<Exposer>(false,e.getMessage());
        }
        return result;
    }

    /**
     * 执行秒杀
     * @param seckillId
     * @param md5
     * @param phone
     * @return
     */
    @PostMapping(value = "/{seckillId}/{md5}/execution")
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId ,
                                                   @PathVariable("md5") String md5 ,
                                                   @CookieValue(value = "killPhone",required = false) Long phone){
        if(phone == null){
            return new SeckillResult<SeckillExecution>(false,"未注册");
        }
        SeckillResult<SeckillExecution> result;
        try {
//            SeckillExecution execution = seckillService.executeSeckill(seckillId,phone,md5);
            /** 使用储存过程执行秒杀 **/
            SeckillExecution execution = seckillService.executeSeckillProcedure(seckillId,phone,md5);
            return new SeckillResult<SeckillExecution>(true,execution);
        } catch (RepeatKillExecption e) {
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStatEnum.REPEAT_KILL);
            return new SeckillResult<SeckillExecution>(true,seckillExecution);
        } catch (SeckillCloseExecption e) {
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStatEnum.END);
            return new SeckillResult<SeckillExecution>(true,seckillExecution);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
            return new SeckillResult<SeckillExecution>(true,seckillExecution);
        }
    }

    /**
     * 获取系统时间
     * @return
     */
    @GetMapping(value = "/time/now")
    @ResponseBody
    public SeckillResult<Long> time(){
        return new SeckillResult(true,System.currentTimeMillis());
    }
}
