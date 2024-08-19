//package com.daesoo.dmotools.common;
//
//import com.zaxxer.hikari.HikariDataSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//@Service
//public class ConnectionPoolMonitorService {
//
//    @Autowired
//    private HikariDataSource hikariDataSource;
//
//    @Scheduled(fixedRate = 1000) // 30초마다 실행
//    public void printConnectionPoolStatus() {
//        System.out.println("Total Connections: " + hikariDataSource.getHikariPoolMXBean().getTotalConnections());
//        System.out.println("Active Connections: " + hikariDataSource.getHikariPoolMXBean().getActiveConnections());
//        System.out.println("Idle Connections: " + hikariDataSource.getHikariPoolMXBean().getIdleConnections());
//        System.out.println("Threads Awaiting Connection: " + hikariDataSource.getHikariPoolMXBean().getThreadsAwaitingConnection());
//    }
//}