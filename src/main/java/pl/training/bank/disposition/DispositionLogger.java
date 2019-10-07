package pl.training.bank.disposition;

import org.aspectj.lang.annotation.*;
import pl.training.bank.BankException;

@Aspect
public class DispositionLogger {

    private static final String SEPARATOR = "##################################################################################################";

    @Before("pl.training.bank.common.Pointcuts.process() && args(disposition)")
    public void onStart(Disposition disposition) {
        System.out.format("%s\n%s\n", SEPARATOR, disposition);
    }

    @AfterReturning("pl.training.bank.common.Pointcuts.process()")
    public void onSuccess() {
        System.out.format("Status: SUCCESS\n%s\n", SEPARATOR);
    }

    @AfterThrowing(value = "pl.training.bank.common.Pointcuts.process()", throwing = "ex")
    public void onException(BankException ex) {
        System.out.format("Status: EXCEPTION (%s)\n%s\n", ex.getClass().getSimpleName(), SEPARATOR);
    }

}
