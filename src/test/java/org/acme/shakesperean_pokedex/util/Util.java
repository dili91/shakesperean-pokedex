package org.acme.shakesperean_pokedex.util;

import com.netflix.hystrix.HystrixCircuitBreaker;
import com.netflix.hystrix.HystrixCommandKey;

import java.lang.reflect.Method;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Util {

    private Util() {
        //util class
    }

    /**
     * Utility to return the circuit breaker around the given method
     *
     * @param clazz      full class name of the class containing the wrapped method
     * @param methodName the name of the method around which a circuit breaker is expected
     * @return the circuit breaker
     */
    public static HystrixCircuitBreaker getCircuitBreaker(Class clazz, String methodName) {
        Method method = Stream.of(clazz.getMethods())
                .filter(m -> m.getName().equals(methodName))
                .findFirst()
                .orElseThrow(() -> (new RuntimeException(methodName + " method not found")));
        String hystrixKey = method.getDeclaringClass().getName().replaceAll("\\.", "_")
                + "#" + methodName
                + Stream.of(method.getParameterTypes()).map(Class::getName).collect(Collectors.joining(",", "(", ")"));
        HystrixCommandKey hystrixCommandKey = HystrixCommandKey.Factory.asKey(hystrixKey);
        return HystrixCircuitBreaker.Factory.getInstance(hystrixCommandKey);
    }
}
