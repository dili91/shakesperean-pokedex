package org.acme.shakesperean_pokedex.util;

import com.netflix.hystrix.HystrixCircuitBreaker;
import com.netflix.hystrix.HystrixCommandKey;

import java.lang.reflect.Method;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.acme.shakesperean_pokedex.util.Configuration.FUN_TRANSLATIONS_API_JSON_STUBS_FOLDER;
import static org.acme.shakesperean_pokedex.util.Configuration.POKE_API_JSON_STUBS_FOLDER;

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

    /**
     * Gets poke api JSON stub file full location
     *
     * @param fileName JSON filename
     * @return the full location
     */
    public static String getPokeApiJsonStubLocation(String fileName) {
        return POKE_API_JSON_STUBS_FOLDER + "/" + fileName;
    }

    /**
     * Gets fun translations api JSON stub file full location
     *
     * @param fileName JSON filename
     * @return the full location
     */
    public static String getFunTranslationsJsonStubLocation(String fileName) {
        return FUN_TRANSLATIONS_API_JSON_STUBS_FOLDER + "/" + fileName;
    }
}
