import java.util.Arrays;

public class l007_Stock_Buy_Sell {

    // b <=== Some Theory to need to understand ================>

    // There are three type of activity you can do.
    // # 1. Buy
    // # 2. Sell (can only sell if you have buyed it)
    // # 3. Rest (don't buy or don't sell or both);

    // # Here Buy is denoted by 1 and sell is denoted by 0.
    // # Transaction is denoted by k.

    // Transaction is only considered when we buy something.

    // ! Hum ek time pe ek he stock hold kar sakte hain.

    // ? So Jis din bhi mai price pay karunga, us din mai kahunga ki meri ek
    // ? transaction hui hai.

    // # dp[i][k][0]
    // ith day mai, jth transaction mai, 0 indicate we want to sell the stock
    // At the end of the day, merepe koi bhi stock nhi hona chahiye.
    // Mujhe sell karke kitna profit mila.

    // # dp[i][k][1]
    // 1 indicate that we want to buy the stock.
    // At the end of the day, merepe ek stock khareeda hua hona chahiye.
    // Mujhe buy karke kitna profit mila.

    // # dp[i][k][0] = Math.max(dp[i-1][k][0], dp[i-1][k][1] + price[i]);

    // dp[i-1][k][0] == > Maine pehle he koi stock bech diya hai i-1 day mai to
    // merepe koi bhi stock nhi hai.

    // dp[i-1][k][1] == > Mujhe bechne ke liye pehle kisi din stock bhi to kharredna
    // hoga.

    // + price[i] == > Jab maine stock ko sell kiya to mujhe kuch paise mile.

    // # But why Math.max ??

    // == > Max isiliye liye kyunki mujhe pehle bech ke jyadafayda hua hoga ya fir
    // aaj bechke jyada fayda hoga.

    // # dp[i][k][1]= Math.max(dp[i-1][k][1], dp[i-1][k-1][0] - price[i]);

    // dp[i-1][k][1] == > Koi stock maine pehle se he khareed rakha hai to mujhe buy
    // karne ki jaroorat nhi hai.

    // dp[i-1][k-1][0] == > Mujhe koi stock khareedne ke liye merepe koi stock nhi
    // hona chahiye. Tabhi mai koi stock ko buy kar paaunga.

    // # Buy why K-1 ??
    // Kyunki jab mai buy karne jaaunga, to wo ek nyi transaction hogi. To mai
    // pichli transaction ki sell value to he lunga na. Aur jo pichla profit hoga,
    // ` usse he to nya buy karunga.

    // - price[i] == > Jo maine keemat pay ki stock ko khareedne ke liye. Ye price
    // mai minus karunga kyunki mai kuch buy kiya hai, to total profit mai se minus
    // to hhoga he.

    // # But why max ?
    // ho sakta hai mujhe pehle khareedne se jyada fayda ho ya aaj khareedne se
    // jyada fayda ho.

    // ? Some cases :

    // # 1. dp[-1][k][0] = 0, Market khula he nhi hai, to bechega kya.
    // # 2. dp[i][0][0] =0, koi transaction he nhi hui hai, to sell karega kya.
    // # 3. dp[-1][k][1] = -∞, market band hai aur tereko buy karne ko bola, to tu
    // # black mai lega samaan with money= -∞. 
    // # 4. dp[i][0][1] = -∞, koi transaction he allowed nhi hai aur tereko buy
    // # karna hai, to tu firse black mai lega with money=-∞. 


    

    // b <================= 121. Best Time to Buy and Sell Stock =============>
    // https://leetcode.com/problems/best-time-to-buy-and-sell-stock/description/

    // Here only single transaction is allowed.
    // Only one time you can buy the stock and only one time sell the stock. so here
    // the k=1;

    // # For k=1, the formula changes to :

    // dp[i][1][0] = Math.max(dp[i-1][1][0], dp[i-1][1][1] + price[i]);
    // dp[i][1][1]= Math.max(dp[i-1][1][1], dp[i-1][0][0] - price[i]);

    // Since dp[i-1][0][0] = 0, therefore the formula changes to ,

    // dp[i][1][0] = Math.max(dp[i-1][1][0], dp[i-1][1][1] + price[i]);
    // dp[i][1][1]= Math.max(dp[i-1][1][1], 0- price[i]);

    // 0 ka reason ye hai ki kyunki transaction sirf 1 hai,
    // To jab mai stock ko khareedne gaya, to use samay sell ke value kuch hogi he
    // nhi kyunki mai he pehla stock khreed raha hunga.

    // # the value in the sell array indicates the amount of profit that we generate
    // # on ith day.

    // # Jo bhi sell ki value hogi last day mai whi humara profit hoga.

    public int maxProfit(int[] prices) {

        int dpi10 = 0, dpi11 = -(int) 1e9; // Ye values -1 day ki hain.

        for (int price : prices) { // Har ek day ke liye price nikalte gaye or pichle value se compare karte gaye.
            dpi10 = Math.max(dpi10, dpi11 + price);
            dpi11 = Math.max(dpi11, 0 - price);
        }

        return dpi10; // Last day sell karke jitna profit aaya, whi humara max profit hoga, to whi
                      // return kar diya.
    }

    // b <=================123. Best Time to Buy and Sell Stock III ==============>
    // https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/description/

    // # Here at most two transactions can be done.
    // # So here the value of k=2.

    // The formula for k=1 remains the same.

    // dp[i][1][0] = Math.max(dp[i-1][1][0], dp[i-1][1][1] + price[i]);
    // dp[i][1][1]= Math.max(dp[i-1][1][1], 0- price[i]);

    // The formula for k=2 will be

    // dp[i][2][0]= Math.max(dp[i-1][2][0], dp[i-1][2][1] + price[i]);
    // dp[i][2][1]= Math.max(dp[i-1][2][1], dp[i-1][1][0] - price[i]);

    // Since in k=2 equation we need the value of dp[i-1][1][0], so it needs to be
    // calculated first using the above two equation for k=1;

    // Why? Dusra stock jab mai khareedne jaaunga, to mujhe pehle ke profit hua tha
    // jo sell karke, uska use karke lunga na nye stock. Tabhi to overall profit
    // nikal payenge.

    // Isiliye jab mai dp[i][2][1] ko calculate karta hun, tabhi to dp[i-1][1][0]
    // ` use karke comapre karta hun.

    // # The answer will be formed in the profit which we get after transaction 2.
    // # Means selling the stock second time. Therefore dp[2][2][0] = > dpi20;

    public int maxProfit_(int[] prices) {

        int dpi10 = 0, dpi11 = -(int) 1e9;
        int dpi20 = 0, dpi21 = -(int) 1e9;

        for (int price : prices) {
            dpi10 = Math.max(dpi10, dpi11 + price);
            dpi11 = Math.max(dpi11, 0 - price);

            dpi20 = Math.max(dpi20, dpi21 + price);
            dpi21 = Math.max(dpi21, dpi10 - price);
        }

        return dpi20;
    }

    // b <============== 122. Best Time to Buy and Sell Stock II ===========>
    // https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/

    // INFINITY Sign === > Alt + 236

    // # For k=1, the formula changes to :

    // dp[i][1][0] = Math.max(dp[i-1][1][0], dp[i-1][1][1] + price[i]);
    // dp[i][1][1]= Math.max(dp[i-1][1][1], dp[i-1][0][0] - price[i]);

    // Since dp[i-1][0][0] = 0, therefore the formula changes to ,

    // dp[i][1][0] = Math.max(dp[i-1][1][0], dp[i-1][1][1] + price[i]);
    // dp[i][1][1]= Math.max(dp[i-1][1][1], 0- price[i]);

    // # Here we can have multiple transaction. There is not limit to number of
    // # transaction. Only thing is to maximize the profit. so K = ∞.

    // # Therefore K + 1 is ∞ and K-1 is also ∞.
    // # There now my equation is independent of k.

    // So the formula becomes :

    // dp[i][0] = Math.max(dp[i - 1][0], dp[i-1][1] + price[i]); == > eq1
    // dp[i][1] = Math.max(dp[i - 1][1], dp[i -1][0] - price[i]); == > eq2

    // # The above equation is same as the K=1 equation, but we have removed the k
    // # variable.

    // # now here I have to know the previous value of dp[i -1][0] to calculate
    // # dp[i][1];

    // Aisa isiliye, kyunki merepe multiple transaction hai. To jab mai buy karne
    // jaaunga, to maine to sell karke profit kamaya hoga pehle transaction se, whi
    // ` use karke to buy karunga.

    // ? So basically the dp[i-1][0] inducates the profit in k-1 transaction.

    public int maxProfit__(int[] prices) {

        int dpi0 = 0, dpi1 = -(int) 1e9;

        for (int price : prices) {
            int dpi_0 = dpi0; // Indicates dp[i-1][0] = dp[i][0]; Storing the prev value.
            dpi0 = Math.max(dpi0, dpi1 + price);
            dpi1 = Math.max(dpi1, dpi_0 - price);
        }

        return dpi0;
    }

    // b <========= Best Time to Buy and Sell Stock with Transaction Fee =========>
    // https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/description/

    // # Logic is same as above. K ∞ hai.
    // # Bas yahan pe har transaction ke liye transaction fees deni hogi.

    // To har buy kyunki hum ek transaction consider karte hain, to buy karte time
    // transaction fee bhi minus karenge profit mai se.

    public int maxProfit(int[] prices, int fee) {

        int dpi0 = 0, dpi1 = -(int) 1e9;

        for (int price : prices) {
            int dpi_0 = dpi0;
            dpi0 = Math.max(dpi0, dpi1 + price);
            dpi1 = Math.max(dpi1, dpi_0 - price - fee);
        }

        return dpi0;
    }

    // b <========== 309. Best Time to Buy and Sell Stock with Cooldown =======>
    // https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/

    // Since humko cooldown bhi include karna hai.
    // Matlab agar maine aaj sell kiya to mai kal dubara buy nhi kar sakta. Mai jo
    // agla buy karunga, wo sell ke next to next day he kar pauunga.

    // Matlab jab mai buy karne jaaunga, to mai jo profit consider karunga wo hoga
    // i-2 day wala. To Meri buy ki equation change hui bas :

    // The equation becomes :

    // dp[i][0] = Math.max(dp[i - 1][0], dp[i-1][1] + price[i]); == > eq1 => Sell
    // dp[i][1] = Math.max(dp[i - 1][1], dp[i -2][0] - price[i]); == > eq2 = >Buy

    // So now somehow I need to have the profit from the i-2 day(dp[i -2][0]).

    // # Yahan pe bhi transaction ki koi limit nhi hai. K=∞.

    public int maxProfit_cool(int[] prices) {

        int dpi0 = 0, dpi1 = -(int) 1e9;
        int dpi_2_0 = 0; // dp[i-2][0];

        for (int price : prices) {
            int dpi_1_0 = dpi0;
            dpi0 = Math.max(dpi0, dpi1 + price);
            dpi1 = Math.max(dpi1, dpi_2_0 - price);
            dpi_2_0 = dpi_1_0;
        }

        return dpi0;
    }

    // b <====================== Best Time to Buy and Sell Stock IV ==============>
    // https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iv/description/

    // # Bas jo generalize formula hai bas whi use kiya hai.
    // # Aur status ke hisab se value hai wo return karayi hai.

    // Status is 0 for sell and 1 for buy.

    // Maine sare variable ki value canculate karli aur uske hisab se best sell and
    // ` best buy and best sell ki us din ki value nikal li.

    public int maxProfit_recu(int[] prices, int i, int k, int status) {

        if (i == -1 || k == 0)
            return status == 1 ? -(int) 1e8 : 0;

        // dp[i][k][0]=Math.max(dp[i-1][k][0], dp[i-1][k][1] + price[i]);
        // dp[i][k][1]=Math.max(dp[i-1][k][1], dp[i-1][k-1][0] - price[i]);

        int dpi_1k0 = maxProfit_recu(prices, i - 1, k, 0);
        int dpi_1k1 = maxProfit_recu(prices, i - 1, k, 1);

        int dpi_1k_10 = maxProfit_recu(prices, i - 1, k - 1, 0);

        int dpik0 = Math.max(dpi_1k0, dpi_1k1 + prices[i]);
        int dpik1 = Math.max(dpi_1k1, dpi_1k_10 - prices[i]);

        return status == 1 ? dpik1 : dpik0;
    }

    public int maxProfit_recu(int k, int[] prices) {
        return maxProfit_recu(prices, prices.length - 1, k, 0); // 0 isiliye pass kiya kyunki humesha hume answer sell
                                                                // pe last day ki i-1 value mai milta hai.
    }

    // Memoisation :

    public int maxProfit_memo(int[] prices, int i, int k, int status, int[][][] dp) {

        if (i == -1 || k == 0)
            return status == 1 ? -(int) 1e8 : 0;

        if (dp[i][k][status] != -(int) 1e9)
            return dp[i][k][status];
        // dp[i][k][0]=Math.max(dp[i-1][k][0], dp[i-1][k][1] + price[i]);
        // dp[i][k][1]=Math.max(dp[i-1][k][1], dp[i-1][k-1][0] - price[i]);

        int dpi_1k0 = maxProfit_memo(prices, i - 1, k, 0, dp);
        int dpi_1k1 = maxProfit_memo(prices, i - 1, k, 1, dp);

        int dpi_1k_10 = maxProfit_memo(prices, i - 1, k - 1, 0, dp);

        int dpik0 = Math.max(dpi_1k0, dpi_1k1 + prices[i]);
        int dpik1 = Math.max(dpi_1k1, dpi_1k_10 - prices[i]);

        return dp[i][k][status] = status == 1 ? dpik1 : dpik0;
    }

    public int maxProfit_memo(int k, int[] prices) {
        int n = prices.length;
        int[][][] dp = new int[n][k + 1][2];
        for (int[][] d : dp) {
            for (int[] dd : d)
                Arrays.fill(dd, -(int) 1e9);
        }

        return maxProfit_memo(prices, prices.length - 1, k, 0, dp);
    }

    // ? In the above code we can also reduce the call. Like shown below.

    public int maxProfit_mem(int[] prices, int i, int k, int status, int[][][] dp) {

        if (i == -1 || k == 0)
            return status == 1 ? -(int) 1e8 : 0;

        if (dp[i][k][status] != -(int) 1e9)
            return dp[i][k][status];
        // dp[i][k][0]=Math.max(dp[i-1][k][0], dp[i-1][k][1] + price[i]);
        // dp[i][k][1]=Math.max(dp[i-1][k][1], dp[i-1][k-1][0] - price[i]);

        int profit = 0;

        if (status == 0) {
            int dpi_1k0 = maxProfit_mem(prices, i - 1, k, 0, dp);
            int dpi_1k1 = maxProfit_mem(prices, i - 1, k, 1, dp);
            int dpik0 = Math.max(dpi_1k0, dpi_1k1 + prices[i]);
            profit = dpik0;

        } else if (status == 1) {
            int dpi_1k1 = maxProfit_mem(prices, i - 1, k, 1, dp);
            int dpi_1k_10 = maxProfit_mem(prices, i - 1, k - 1, 0, dp);
            int dpik1 = Math.max(dpi_1k1, dpi_1k_10 - prices[i]);
            profit = dpik1;
        }

        return dp[i][k][status] = profit;
    }

    public int maxProfit_mem(int k, int[] prices) {
        int n = prices.length;
        int[][][] dp = new int[n][k + 1][2];
        for (int[][] d : dp) {
            for (int[] dd : d)
                Arrays.fill(dd, -(int) 1e9);
        }

        return maxProfit_mem(prices, prices.length - 1, k, 0, dp);
    }

}