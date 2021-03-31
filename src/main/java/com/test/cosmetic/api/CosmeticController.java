package com.test.cosmetic.api;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController

public class CosmeticController {
	
    List<Cosmetic> cosmetics = new ArrayList<>(Arrays.asList(
        new Cosmetic("Hellow", "Jojo abcd", 1),
        new Cosmetic("Hellow", "P Bank 888", 1)
    ));
    
    @RequestMapping("/cosmetics")
    	public List<Cosmetic> getAllCosmetics() {
        return cosmetics;
    }
    
}
