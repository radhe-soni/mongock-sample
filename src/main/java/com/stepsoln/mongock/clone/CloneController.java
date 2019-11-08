package com.stepsoln.mongock.clone;

import java.util.Collections;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.stepsoln.mongock.clone.carrier.CarrierCloneRequest;
import com.stepsoln.mongock.clone.carrier.CarrierCloneService;
import com.stepsoln.mongock.clone.product.ProductCloneRequest;
import com.stepsoln.mongock.clone.product.ProductCloneService;

@RestController
@RequestMapping("/clone")
public class CloneController
{
	@Autowired
	CarrierCloneService carrierCloneService;
	
	@Autowired
	ProductCloneService productCloneService;

	@PostMapping("/carrier")
    public Map<String, String> cloneCarrier(@RequestBody CarrierCloneRequest request) {
         return Collections.singletonMap("message", carrierCloneService.clone(request));
    }
	
	@PostMapping("/product")
    public Map<String, String> cloneCarrier(@RequestBody ProductCloneRequest request) {
         return Collections.singletonMap("message", productCloneService.clone(request));
    }
}
