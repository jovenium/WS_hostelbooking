
/**
 * AdditionServiceSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.1  Built on : Oct 19, 2009 (10:59:00 EDT)
 */
    package addition;
    /**
     *  AdditionServiceSkeleton java skeleton for the axisService
     */
    public class AdditionServiceSkeleton implements AdditionServiceSkeletonInterface{
        
         
        /**
         * Auto generated method signature
         * 
                                     * @param additionner0
         */
        
                 public calculette.AdditionnerResponse additionner
                  (
                  calculette.Additionner additionner0
                  )
            {
                int a = additionner0.getEntier1();
		int b = additionner0.getEntier2();
		calculette.AdditionnerResponse resp = new 			calculette.AdditionnerResponse();	
		resp.setResultat1(a+b);
		return resp;
        }
     
    }
    
