package pl.training.bank;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;

import java.util.Collection;
import java.util.Random;

public class RandomVoter implements AccessDecisionVoter<Object> {

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        System.out.println(configAttribute);
        return true;
    }

    @Override
    public int vote(Authentication authentication, Object o, Collection collection) {
        Random random = new Random();
        return random.nextBoolean() ? ACCESS_GRANTED : ACCESS_DENIED;
    }

    @Override
    public boolean supports(Class aClass) {
        System.out.println(aClass);
        return true;
    }

}
