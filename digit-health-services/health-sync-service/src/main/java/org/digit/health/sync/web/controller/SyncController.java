package org.digit.health.sync.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.digit.health.sync.service.SyncService;
import org.digit.health.sync.utils.ModelMapper;
import org.digit.health.sync.web.models.SyncId;
import org.digit.health.sync.web.models.request.SyncUpMapper;
import org.digit.health.sync.web.models.request.SyncUpRequest;
import org.digit.health.sync.web.models.response.SyncUpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@Slf4j
@RequestMapping("/sync/v1")
public class SyncController {

    private final SyncService syncService;

    @Autowired
    public SyncController(@Qualifier("fileSyncService") SyncService syncService) {
        this.syncService = syncService;
    }


    @PostMapping("/up")
    public ResponseEntity<SyncUpResponse> syncUp(@RequestBody @Valid SyncUpRequest syncUpRequest) {
        log.info("Sync up request {}", syncUpRequest.toString());
        SyncId syncId = syncService.syncUp(SyncUpMapper.INSTANCE.toDTO(syncUpRequest));
        log.info("Generated sync id {}", syncId);
        return ResponseEntity.accepted().body(SyncUpResponse.builder()
                .responseInfo(ModelMapper.createResponseInfoFromRequestInfo(syncUpRequest
                        .getRequestInfo(), true))
                .syncId(syncId.getSyncId())
                .build());
    }
}