package com.mark.netty.actioin.common;

import lombok.Data;

@Data
public class MessageHeader {

    private int version = 1;
    private long streamId;
    private int opCode;
}
