package com.perso.back.task_planner.aop;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Component
public class MethodParameters {

    public static final String PARAMETER_0 = "parameter_0";
    public static final String PARAMETER_1 = "parameter_1";
    public static final String PARAMETER_2 = "parameter_2";

    /**
     * Cherche les paramètres demandés dans un joinPoint et les renvoie dans une map.
     * Si une sous liste de parameterNames contient plusieurs éléments,
     * on rajoute dans la map le premier paramètre trouvé parmis les noms dans la sous liste.
     *
     * @param joinPoint      représente une méthode
     * @param parameterNames les noms des paramètres recherchés
     * @return Une map (avec le nom du paramètre en clé et le paramètre en valeur)
     */
    public Optional<Map<String, Object>> getMap(ProceedingJoinPoint joinPoint, String[][] parameterNames) {
        Map<String, Object> map = new HashMap<>();
        int index = 0;

        for (String[] sameParameterName : parameterNames) {
            boolean found = false;

            for (String parameterName : sameParameterName) {
                Optional<Object> optionalO = getParameter(joinPoint, parameterName);

                if (optionalO.isPresent()) {
                    found = true;
                    map.put(String.format("parameter_%d", index), optionalO.get());
                    break;
                }
            }

            if (!found) {

                return Optional.empty();
            }

            index++;
        }

        return Optional.of(map);
    }

    private Optional<Object> getParameter(ProceedingJoinPoint joinPoint, String parameterName) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] methodParameterNames = methodSignature.getParameterNames();
        int index = 0;

        for (String methodParameterName : methodParameterNames) {

            if (parameterName.equals(methodParameterName)) {

                return Optional.of(joinPoint.getArgs()[index]);
            }

            index++;
        }

        return Optional.empty();
    }
}

