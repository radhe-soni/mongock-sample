package com.stepsoln.mongock.clone.carrier;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.stepsoln.mongock.clone.CloneException;
import com.stepsoln.mongock.clone.DBCloneService;

@Service
public class CarrierCloneService
{
	@Value("${carrier.db.suffix:}")
	private String dbSuffix;
	@Value("${carrier.db.name.format:%s%s}")
	private String dbNameFormat;

	@Autowired
	private DBCloneService dbCloneService;

	public String clone(CarrierCloneRequest request)
	{
		try
		{
			List<String> selectedCollections = new ArrayList<>(request.getSelectedProducts());
			selectedCollections.add("mongockLock");
			selectedCollections.add("mongockChangeLog");
			dbCloneService.clone(getDBNameForCarrier(request.getSourceCode()), getDBNameForCarrier(request.getTargetCode()),
					selectedCollections);
			String products = request.getSelectedProducts().stream().collect(Collectors.joining(", "));
			return String.format("Carrier %s created with products %s ", request.getTargetCode(), products);
		}
		catch (CloneException e)
		{
			throw new CloneException("Carrier cloning failed. Possible causes : duplicate carrier code ", e);
		}
	}

	public String getDBNameForCarrier(String carrierCode)
	{
		return String.format(dbNameFormat, carrierCode, dbSuffix);
	}

}
