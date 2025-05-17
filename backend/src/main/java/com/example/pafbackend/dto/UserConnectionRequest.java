package com.example.pafbackend.dto;

import lombok.Data;
import java.util.List;

@Data
public class UserConnectionRequest {
    private String userId;
    private List<String> friendIds;
}
