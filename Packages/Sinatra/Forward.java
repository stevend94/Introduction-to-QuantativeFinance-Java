/*This class shall represent a Forward contract asset which is a legally binding contract
  to either buy(long in the forward) or sell(short in the forward) an asset at an agreed
  price (strike price) at a certain time (expiry/maturity date). The pay-off function
  (for a long position) is given by F = S - X where S is the asset value and X is the
  strike price at time T.

  In this case the value is the strike price, it should be also noted that these Forward
  contracts are negotiated between the two parties instead of an exchange, though this
  shall have no affect on our pricing model.*/


package Packages.Sinatra;

import Packages.GlobalVariables;
import Packages.Position;

public class Forward extends Derivative {

    //Default constructor for Forward class object
    public Forward(Asset new_asset, float new_strike, float new_maturity, Position new_position) {
      super(new_asset, new_strike, new_maturity, new_position);
    }

    //Alternative constructor for Forward class object which creates the asset aswell
    public Forward(float new_price, String new_name, float new_drift_rate, float new_volatility,
                      float new_strike, float new_maturity, Position new_position)
    {
      super(new_price, new_name, new_drift_rate, new_volatility,
            new_strike, new_maturity, new_position);
    }

    //Function to change strike price to no arbitrage value
    public void fixStrike() { this.strike = this.value; }

    //Pay-off method for forward contracts given financial position
    public float payOff(float asset_value) {
      float pay_off = 0f;
      switch (position) {
        case SHORT:
             pay_off = strike - asset_value;      //Short position case, F = X - S
             break;

        case LONG:
            pay_off = asset_value - strike;       //Long position case, F = S - X
            break;
      }
      return pay_off;
    }

    void UpdateValue() {
      //Function constructed for Forward contract using non arbitrage argument F=Se^RT
      this.value = asset.getPrice()*((float)java.lang.Math.exp(GlobalVariables.INTEREST*getMaturity()));
    }

}
