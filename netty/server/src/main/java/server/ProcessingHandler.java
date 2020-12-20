package server;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ProcessingHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {

        RequestData requestData = (RequestData) msg;
        ResponseData responseData = new ResponseData();

        String[] mas_str = requestData.getStr().split(" ");
        int result = 0;
        if ("+".equals(mas_str[1])) {
            result = Integer.parseInt(mas_str[0]) + Integer.parseInt(mas_str[2]);
        } else if ("-".equals(mas_str[1])) {
            result = Integer.parseInt(mas_str[0]) - Integer.parseInt(mas_str[2]);
        } else if ("*".equals(mas_str[1])) {
            result = Integer.parseInt(mas_str[0]) * Integer.parseInt(mas_str[2]);
        } else if ("/".equals(mas_str[1])) {
            result = Integer.parseInt(mas_str[0]) / Integer.parseInt(mas_str[2]);
        }

        System.out.println(result);
        responseData.setIntValue(result);
        ChannelFuture future = ctx.writeAndFlush(responseData);
        future.addListener(ChannelFutureListener.CLOSE);
    }
}