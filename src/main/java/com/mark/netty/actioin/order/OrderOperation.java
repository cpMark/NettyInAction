package com.mark.netty.actioin.order;

import com.mark.netty.actioin.common.Operation;
import com.mark.netty.actioin.common.OperationResult;
import lombok.Data;
import lombok.extern.java.Log;

@Data
@Log
public class OrderOperation extends Operation {

    private int tableId;
    private String dish;

    public OrderOperation(int tableId,String dish){
        this.tableId = tableId;
        this.dish = dish;
    }

    @Override
    public OperationResult execute() {
        log.info("order's executing startup with orderRequest: "+toString());
        //execute order logic
        log.info("order's executing complete");
        OrderOperationResult response = new OrderOperationResult(tableId,dish,true);
        return response;
    }
}
