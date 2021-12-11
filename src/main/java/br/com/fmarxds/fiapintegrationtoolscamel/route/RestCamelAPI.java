package br.com.fmarxds.fiapintegrationtoolscamel.route;

import br.com.fmarxds.fiapintegrationtoolscamel.bean.MyBean;
import br.com.fmarxds.fiapintegrationtoolscamel.service.MyBeanService;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MediaType;

@Component
public class RestCamelAPI extends RouteBuilder {

    @Value("${server.port}")
    private String serverPort;

    @Value("${baeldung.api.path}")
    private String contextPath;

    @Override
    public void configure() throws Exception {

        CamelContext context = new DefaultCamelContext();

        // http://localhost:8080/camel/api-doc
        restConfiguration().contextPath(contextPath)
                .port(serverPort)
                .enableCORS(true)
                .apiContextPath("/api-doc")
                .apiProperty("api.title", "Test REST API")
                .apiProperty("api.version", "v1")
                .apiProperty("cors", "true")
                .apiContextRouteId("doc-api")
                .component("servlet")
                .bindingMode(RestBindingMode.json)
                .dataFormatProperty("prettyPrint", "true");

        rest("/api/").description("Teste REST Service")
                .id("api-route")
                .post("/bean")
                .produces(MediaType.APPLICATION_JSON)
                .consumes(MediaType.APPLICATION_JSON)
                .bindingMode(RestBindingMode.auto)
                .type(MyBean.class)
                .enableCORS(true)
                .to("direct:remoteService");

        from("direct:remoteService").routeId("direct-route")
                .tracing()
                .log(">>> INCOMING: ${body.id}: ${body.name}")
                .process(exchange -> {
                    MyBean bodyIn = (MyBean) exchange.getIn().getBody();
                    MyBeanService.example(bodyIn);
                    exchange.getIn().setBody(bodyIn);
                })
                .log(">>> OUTCOMING: ${body.id}: ${body.name}")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201));

    }

}
