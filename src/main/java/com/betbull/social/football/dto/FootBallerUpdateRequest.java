package com.betbull.social.football.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FootBallerUpdateRequest extends FootBallerRequest {

    private Long footballerId;


}
