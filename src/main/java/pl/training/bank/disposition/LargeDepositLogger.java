package pl.training.bank.disposition;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

@Aspect
public class LargeDepositLogger implements ApplicationEventPublisherAware {

    private static final long LARGE_DEPOSIT = 50_000;

    private ApplicationEventPublisher publisher;

    @AfterReturning("pl.training.bank.common.Pointcuts.process() && args(disposition)")
    public void onLargeDeposit(Disposition disposition) {
        if (disposition.getFunds() >= LARGE_DEPOSIT) {
            LargeDepositEvent largeDepositEvent = new LargeDepositEvent(this);
            largeDepositEvent.setDisposition(disposition);
            publisher.publishEvent(largeDepositEvent);
        }
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        publisher = applicationEventPublisher;
    }

}
