package com.mycompany.canaban;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.server.environment.MultipleJGitEnvironmentRepository;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Map;

/**
 * Created by antongusev on 20.05.17.
 */
@ControllerAdvice
public class CustomInterceptor implements ResponseBodyAdvice<Environment> {

    @Autowired
    private MultipleJGitEnvironmentRepository repository;


    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Environment beforeBodyWrite(Environment environment, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        String[] params = serverHttpRequest.getURI().getPath().split("/");
        Environment commonEnvironment = repository.findOne("common", params[2], params.length > 3 ? params[3] : null);
        Map props = environment.getPropertySources().get(0).getSource();
        commonEnvironment.getPropertySources().get(0).getSource().entrySet().stream().forEach(entry -> props.put(entry.getKey(), entry.getValue()));
        return environment;
    }

//    @SuppressWarnings("unchecked")
//    private static <T> T createProxy(final Class<MultipleJGitEnvironmentRepository> classToMock,
//                                     final InvocationHandler interceptor) {
//        final Enhancer enhancer = new Enhancer();
//        enhancer.setSuperclass(classToMock);
//        enhancer.setCallbackType(interceptor.getClass());
//
//        final Class<?> proxyClass = enhancer.createClass();
//        Enhancer.registerCallbacks(proxyClass, new Callback[] { interceptor });
//        return (T) ObjenesisHelper.newInstance(proxyClass);
//    }
//
//    final InvocationHandler enrichCommonConfig = new InvocationHandler() {
//
//        @Override
//        public Object invoke(Object environmentRepository, Method method, Object[] objects) throws Throwable {
//            Method findOne = environmentRepository.getClass().getDeclaredMethod("findOne", String.class, String.class, String.class);
//            if (method.equals(findOne)) {
//                Environment environment = (Environment) method.invoke(environmentRepository, objects);
//                Environment commonEnvironment = (Environment) method.invoke(environment, "common", objects[1], objects[2]);
//                Map props = environment.getPropertySources().get(0).getSource();
//                commonEnvironment.getPropertySources().get(0).getSource().entrySet().stream().forEach(entry -> props.put(entry.getKey(), entry.getValue()));
//                return environment;
//            }
//            return method.invoke(environmentRepository, objects);
//        }
//    };

}
