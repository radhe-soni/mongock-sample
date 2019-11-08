package com.stepsoln.mongock.clone.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCloneRequest
{
	private String carrierCode;
	private String sourceCode;
	private String targetCode;
}
