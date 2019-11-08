package com.stepsoln.mongock.clone.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.stepsoln.mongock.clone.CloneException;
import com.stepsoln.mongock.clone.CollectionCloneService;
import com.stepsoln.mongock.clone.carrier.CarrierCloneService;

@Service
public class ProductCloneService
{
	@Value("${product.collection.name.format:%s%s}")
	private String collectionNameFormat;

	@Autowired
	private CarrierCloneService carrierCloneService;

	@Autowired
	private CollectionCloneService collectionCloneService;

	public String clone(ProductCloneRequest request)
	{
		String dbName = carrierCloneService.getDBNameForCarrier(request.getCarrierCode());
		try
		{
			collectionCloneService.clone(dbName, request.getTargetCode(), request.getSourceCode());
		}
		catch (CloneException e)
		{
			throw new CloneException("Product cloning failed. Possible causes : duplicate product code ", e);
		}
		return String.format("Product %s cloned from product %s", request.getTargetCode(), request.getSourceCode());
	}

}
