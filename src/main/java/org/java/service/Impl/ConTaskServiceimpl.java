package org.java.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.java.entity.pojo.ConTask;
import org.java.entity.pojo.Internshiptask;
import org.java.mapper.ConTaskMapper;
import org.java.mapper.InternshiptaskMapper;
import org.java.service.ConTaskService;
import org.springframework.stereotype.Service;

@Service
public class ConTaskServiceimpl extends ServiceImpl<ConTaskMapper, ConTask> implements ConTaskService {

}
