package com.cx.dubbox.api.service.service;

import com.cx.dubbox.api.common.entity.ApiEntity;

public interface IapiInterfaceService {

    ApiEntity queryApiInterfaceByApiId(String apiId,String version);

}
