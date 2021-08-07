package com.atjh.demoafter.controller;

import cn.hutool.core.util.StrUtil;
import com.atjh.demoafter.common.Result;
import com.atjh.demoafter.entity.News;
import com.atjh.demoafter.mapper.NewsMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/news")
public class NewsController {

    @Resource
    NewsMapper newsMapper;

    @PostMapping
    public Result<?> save(@RequestBody News news) {
        newsMapper.insert(news);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody News news) {
        newsMapper.updateById(news);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable Long id) {
        newsMapper.deleteById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        return Result.success(newsMapper.selectById(id));
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<News> wrapper = Wrappers.<News>lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(News::getTitle, search);
        }
        Page<News> newsPage = newsMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(newsPage);
    }
}
