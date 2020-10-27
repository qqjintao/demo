package com.intertek.demo.monitor.controller;

import com.intertek.demo.common.annotation.ControllerEndPoint;
import com.intertek.demo.common.entity.ItsResponse;
import com.intertek.demo.common.exception.RedisConnectException;
import com.intertek.demo.monitor.service.IRedisService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

/**
 * @author jacksy.qin
 * @date 2019/9/23 16:44
 */
@RestController
@RequestMapping("redis")
public class RedisController {

    private static final String INTEGER_PREFIX = "(integer) ";

    @Autowired
    private IRedisService redisService;

    @RequestMapping("keysSize")
    @RequiresPermissions("redis:view")
    public Map<String, Object> getKeysSize() throws RedisConnectException {
        return redisService.getKeysSize();
    }

    @RequestMapping("memoryInfo")
    @RequiresPermissions("redis:view")
    public Map<String, Object> getMemoryInfo() throws RedisConnectException {
        return redisService.getMemoryInfo();
    }

    @GetMapping("keys")
    @ControllerEndPoint(operation = "执行Redis keys命令")
    public ItsResponse keys(String arg) throws RedisConnectException {
        Set<String> set = this.redisService.getKeys(arg);
        return new ItsResponse().success().data(set);
    }

    @GetMapping("get")
    @ControllerEndPoint(operation = "执行Redis get命令")
    public ItsResponse get(String arg) throws RedisConnectException {
        String result = this.redisService.get(arg);
        return new ItsResponse().success().data(result == null ? "" : result);
    }

    @GetMapping("set")
    @ControllerEndPoint(operation = "执行Redis set命令")
    public ItsResponse set(String arg) throws RedisConnectException {
        String[] args = arg.split(",");
        if (args.length == 1) {
            return new ItsResponse().fail().data("(error) ERR wrong number of arguments for 'set' command");
        }else if (args.length != 2) {
            return new ItsResponse().fail().data("(error) ERR syntax error");
        }
        String result = this.redisService.set(args[0], args[1]);
        return new ItsResponse().success().data(result);
    }

    @GetMapping("del")
    @ControllerEndPoint(operation = "执行Redis del命令")
    public ItsResponse del(String arg) throws RedisConnectException {
        String[] args = arg.split(",");
        Long result = this.redisService.del(args);
        return new ItsResponse().success().data(INTEGER_PREFIX + result);
    }

    @GetMapping("exists")
    @ControllerEndPoint(operation = "执行Redis exists命令")
    public ItsResponse exists(String arg) throws RedisConnectException {
        int count = 0;
        String[] arr = arg.split(",");
        for (String key : arr) {
            if (this.redisService.exists(key)) {
                count++;
            }
        }
        return new ItsResponse().success().data(INTEGER_PREFIX + count);
    }

    @GetMapping("pttl")
    @ControllerEndPoint(operation = "执行Redis pttl命令")
    public ItsResponse pttl(String arg) throws RedisConnectException {
        return new ItsResponse().success().data(INTEGER_PREFIX + this.redisService.pttl(arg));
    }

    @GetMapping("pexpire")
    @ControllerEndPoint(operation = "执行Redis pexpire命令")
    public ItsResponse pexpire(String arg) throws RedisConnectException {
        String[] arr = arg.split(",");
        if (arr.length != 2 || !isValidLong(arr[1])) {
            return new ItsResponse().fail().data("(error) ERR wrong number of arguments for 'pexpire' command");
        }
        return new ItsResponse().success().data(INTEGER_PREFIX + this.redisService.pexpire(arr[0], Long.valueOf(arr[1])));
    }

    private static boolean isValidLong(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
