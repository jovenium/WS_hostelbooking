
/**
 * ReservationCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.1  Built on : Oct 19, 2009 (10:59:00 EDT)
 */

    package ws.reservation;

    /**
     *  ReservationCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class ReservationCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public ReservationCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public ReservationCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for makeReservation method
            * override this method for handling normal response from makeReservation operation
            */
           public void receiveResultmakeReservation(
                    ws.reservation.ReservationStub.MakeReservationResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from makeReservation operation
           */
            public void receiveErrormakeReservation(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for listHotel method
            * override this method for handling normal response from listHotel operation
            */
           public void receiveResultlistHotel(
                    ws.reservation.ReservationStub.ListHotelResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from listHotel operation
           */
            public void receiveErrorlistHotel(java.lang.Exception e) {
            }
                


    }
    