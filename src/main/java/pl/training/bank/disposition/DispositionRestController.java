package pl.training.bank.disposition;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.training.bank.common.mapper.Mapper;

import javax.validation.Valid;

@RequestMapping("${baseUrl}/dispositions")
@RequiredArgsConstructor
@RestController
public class DispositionRestController {

    @NonNull
    private DispositionService dispositionService;
    @NonNull
    private DispositionMapper dispositionMapper;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity process(@RequestBody @Valid DispositionTo dispositionTo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        Disposition disposition = dispositionMapper.map(dispositionTo);
        dispositionService.process(disposition);
        return ResponseEntity.noContent().build();
    }

}
