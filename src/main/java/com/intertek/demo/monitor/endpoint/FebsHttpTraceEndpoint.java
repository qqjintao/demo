package com.intertek.demo.monitor.endpoint;

import com.intertek.demo.common.annotation.ItsEndPoint;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;

import java.util.List;

/**
 * @author jacksy.qin
 * @date 2019/9/23 16:44
 */
@ItsEndPoint
public class FebsHttpTraceEndpoint {

    private final HttpTraceRepository repository;

    public FebsHttpTraceEndpoint(HttpTraceRepository repository) {
        this.repository = repository;
    }

    public FebsHttpTraceDescriptor traces() {
        return new FebsHttpTraceDescriptor(this.repository.findAll());
    }

    public static final class FebsHttpTraceDescriptor {

        private final List<HttpTrace> traces;

        private FebsHttpTraceDescriptor(List<HttpTrace> traces) {
            this.traces = traces;
        }

        public List<HttpTrace> getTraces() {
            return this.traces;
        }
    }
}
