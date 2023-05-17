package com.acme.sandbox.gateway.loadbalance;

import java.text.DecimalFormat;

public class LoadBalancerMetrics {

    private static final DecimalFormat df = new DecimalFormat("0.0");

    private Long defaultRouteCount = 0L;

    private Long experimentalRouteCount = 0L;

    public void incrementDefaultCount() {
        this.defaultRouteCount++;
    }

    public void incrementExperimentalCount() {
        this.experimentalRouteCount++;
    }

    @Override
    public String toString() {
        var totalCount = (double) experimentalRouteCount + defaultRouteCount;
        var percentExperimental = experimentalRouteCount / totalCount * 100;
        var percentDefault = defaultRouteCount / totalCount * 100;
        return String.format("[loadBalancingReport]: count=%d, experimental=%d (%s%%), default=%d (%s%%)",
                (int) totalCount,
                experimentalRouteCount,
                df.format(percentExperimental),
                defaultRouteCount,
                df.format(percentDefault));
    }

}
