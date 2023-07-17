package kopo.poly.controller;

import kopo.poly.service.ICovidService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/covid")
@RequiredArgsConstructor
@RestController
public class CovidRestController {
    private final ICovidService covidService;

    @GetMapping("apiCovid")
    public String main()throws Exception{
        log.info(this.getClass().getName() + "");
        String result = covidService.getCovidRes();
        return result;
    }
}
