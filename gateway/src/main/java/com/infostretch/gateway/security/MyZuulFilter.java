package com.infostretch.gateway.security;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MyZuulFilter extends ZuulFilter {

    private final JwtHelper jwtHelper;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("CAME HERE");
        System.out.println(jwtHelper.getCurrentUser());
        System.out.println(jwtHelper.getCurrentUser().getId());
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.addZuulRequestHeader("userId", jwtHelper.getCurrentUser().getId().toString());
        System.out.println("Header added , " + jwtHelper.getCurrentUser().getId());
        return null;
    }
}
