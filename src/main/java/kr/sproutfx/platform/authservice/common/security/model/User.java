package kr.sproutfx.platform.authservice.common.security.model;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class User {
    private String id;
    private String nId;
    private String name;
    private String email;
    private String password;
    private String passwordExpired;
    private String description;
    private boolean isLocked;
    private Timestamp createdOn;
    private String createUserId;
    private Timestamp lastUpdatedOn;
    private String lastUpdateUserId;
}