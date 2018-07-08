package com.cx.dubbox.api.service.service;

import java.util.List;

public interface IloadBalanceService {

    String chooseOne(String apiId,String version,List<String> set);

}
