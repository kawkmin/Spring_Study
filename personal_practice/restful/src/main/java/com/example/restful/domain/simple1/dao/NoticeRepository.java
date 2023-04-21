package com.example.restful.domain.simple1.dao;

import com.example.restful.domain.simple1.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

}
