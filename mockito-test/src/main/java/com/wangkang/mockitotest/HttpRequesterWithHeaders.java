package com.wangkang.mockitotest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangkang
 * @date 2020/4/7- 22:11
 * @since
 */
public class HttpRequesterWithHeaders {
    private HttpBuilder builder;
    public HttpRequesterWithHeaders(HttpBuilder builder) {
        this.builder = builder;
    }
    public String request(String uri) {
        return builder.withUrl(uri)
                .withHeader("Content-type: application/json")
                .withHeader("Authorization: Bearer")
                .request();
    }
    public static class HttpBuilder {
        private String uri;
        private List<String> headers;
        public HttpBuilder() {
            this.headers = new ArrayList<String>();
        }
        public HttpBuilder withUrl(String uri) {
            this.uri = uri;
            return this;
        }
        public HttpBuilder withHeader(String header) {
            this.headers.add(header);
            return this;
        }
        public String request() {
            return uri + headers.toString();
        }
    }
}
