package com.dl.demo.web.controller.feign;

import com.example.common.api.contract.AuthApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "${client.auth.name}", url = "${client.auth.url}")
public interface AuthClient extends AuthApi {
}
