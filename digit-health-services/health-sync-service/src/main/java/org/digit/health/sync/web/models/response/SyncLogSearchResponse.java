package org.digit.health.sync.web.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.digit.health.sync.web.models.dao.SyncLogData;
import org.egov.common.contract.response.ResponseInfo;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SyncLogSearchResponse {

    @JsonProperty("responseInfo")
    private ResponseInfo responseInfo;

    @JsonProperty("syncs")
    private List<SyncLogData> syncLogDataResults;
}
