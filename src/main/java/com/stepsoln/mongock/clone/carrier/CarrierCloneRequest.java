package com.stepsoln.mongock.clone.carrier;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarrierCloneRequest
{
	private String sourceCode;
	private String targetCode;
	private List<String> selectedProducts;
}
