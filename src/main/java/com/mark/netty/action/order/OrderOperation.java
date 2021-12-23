package com.mark.netty.action.order;

import com.google.common.util.concurrent.Uninterruptibles;
import com.mark.netty.action.common.Operation;
import com.mark.netty.action.common.OperationResult;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Data
@Slf4j
public class OrderOperation extends Operation {

    private int tableId;
    private String dish;

    public OrderOperation(int tableId, String dish) {
        this.tableId = tableId;
        this.dish = dish;
    }

    @Override
    public OperationResult execute() {
        log.info("order's executing startup with orderRequest: " + toString());
        //execute order logic
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
        log.info("order's executing complete");
        OrderOperationResult response = new OrderOperationResult(tableId, dish, true);
        return response;
    }
}
