package com.example.entity;

import lombok.Data;

@Data
public class ApprovalRes extends Approval{

    private String username;
    private String adminname;
    private String goodsname;
    private String storagename;
    private String goodstypename;
}
