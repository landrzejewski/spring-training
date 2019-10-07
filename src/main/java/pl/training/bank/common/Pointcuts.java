package pl.training.bank.common;

import org.aspectj.lang.annotation.Pointcut;

public interface Pointcuts {

    @Pointcut("execution(void pl.training.bank.disposition.DispositionService.process(..))")
    static void process() {
    }

}
