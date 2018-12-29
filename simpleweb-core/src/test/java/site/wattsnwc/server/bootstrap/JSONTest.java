package site.wattsnwc.server.bootstrap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * msg
 *
 * @author watts
 */
public class JSONTest {
    /**
     * asd : 1
     * dsa : 2
     */

    private int asd;
    private int dsa;

    @Test
    public void testJsonObj() {
        //comment
        String s = "{\"kpiName\":\"双高案件一次核损通过率\",\"subOrgKpiDatas\":{\"43000000\":{\"value\":42.16,\"diff\":9.83},\"53000000\":{\"value\":33.43,\"diff\":10.16},\"15000000\":{\"value\":18.48,\"diff\":-8.37},\"21020000\":{\"value\":15.91,\"diff\":-5.8},\"35000000\":{\"value\":42.47,\"diff\":3.58},\"45000000\":{\"value\":4.11,\"diff\":-18.87},\"44030000\":{\"value\":56.21,\"diff\":11.71},\"31000000\":{\"value\":3.09,\"diff\":-31.36},\"41000000\":{\"value\":41.0,\"diff\":7.76},\"51000000\":{\"value\":21.86,\"diff\":-3.8},\"61000000\":{\"value\":28.86,\"diff\":0.22},\"21000000\":{\"value\":41.26,\"diff\":-7.42},\"37000000\":{\"value\":12.39,\"diff\":-20.09},\"22000000\":{\"value\":15.48,\"diff\":-25.33},\"12000000\":{\"value\":27.78,\"diff\":4.36},\"64000000\":{\"value\":24.32,\"diff\":-2.71},\"54000000\":{\"value\":30.61,\"diff\":-0.42},\"33020000\":{\"value\":43.27,\"diff\":7.92},\"35020000\":{\"value\":28.57,\"diff\":-17.92},\"14000000\":{\"value\":35.55,\"diff\":15.79},\"34000000\":{\"value\":34.63,\"diff\":4.21},\"44000000\":{\"value\":33.28,\"diff\":0.31},\"52000000\":{\"value\":44.83,\"diff\":18.64},\"42000000\":{\"value\":38.37,\"diff\":11.19},\"62000000\":{\"value\":41.82,\"diff\":5.09},\"32000000\":{\"value\":40.02,\"diff\":3.3},\"11000000\":{\"value\":34.56,\"diff\":4.62},\"65000000\":{\"value\":29.63,\"diff\":-2.24},\"36000000\":{\"value\":45.95,\"diff\":13.61},\"46000000\":{\"value\":40.0,\"diff\":6.16},\"37020000\":{\"value\":12.97,\"diff\":-26.71},\"50000000\":{\"value\":17.34,\"diff\":-5.33},\"13000000\":{\"value\":29.45,\"diff\":10.79},\"23000000\":{\"value\":38.38,\"diff\":17.8},\"33000000\":{\"value\":56.17,\"diff\":17.51},\"63000000\":{\"value\":36.62,\"diff\":8.07}}}";
        JSONObject t = JSON.parseObject(s);
        JSONObject sub = t.getJSONObject("subOrgKpiDatas");
//        System.out.println(sub);
        Set<Map.Entry<String, Object>> entries = sub.entrySet();
        List<Value> vs = new ArrayList<>(entries.size());
        entries.forEach(entry -> {
            JSONObject v = (JSONObject) entry.getValue();
            Value v2 = new Value();
            v2.key = entry.getKey();
            v2.diff = v.getDoubleValue("diff");
            v2.value = v.getDoubleValue("value");
            vs.add(v2);
        });
        vs.sort((o1, o2) -> {
            if (o1.value < o2.value) {
                return 1;
            } else if (o1.value > o2.value) {
                return -1;
            }
            return 0;
        });
        StringBuilder absTop = new StringBuilder();
        vs.stream().limit(5).forEach(value -> absTop.append(value.key).append(","));
        System.out.println("abs sql:");
        System.out.println("select * from v_top_org_area  t where t.com_code in("+absTop.deleteCharAt(absTop.length()-1).toString()+");");
        StringBuilder absBottom = new StringBuilder();
        vs.stream().skip(vs.size()-5).forEach(value -> absBottom.append(value.key).append(","));
        System.out.println("select * from v_top_org_area  t where t.com_code in("+absBottom.deleteCharAt(absBottom.length()-1).toString()+");");
        System.out.println("绝对值：");
        List<Value> vs2 = new ArrayList<>(10);
        vs2.addAll(vs.stream().limit(5).collect(Collectors.toList()));
        vs2.addAll(vs.stream().skip(vs.size()-5).collect(Collectors.toList()));
        System.out.println(vs2);
        vs.sort((o1, o2) -> {
            if (o1.diff > o2.diff) {
                return 1;
            } else if (o1.diff < o2.diff) {
                return -1;
            }
            return 0;
        });
        StringBuilder diffTop = new StringBuilder();
        vs.stream().limit(5).forEach(value -> diffTop.append(value.key).append(","));
        System.out.println("diff sql:");
        System.out.println("select * from v_top_org_area  t where t.com_code in("+diffTop.deleteCharAt(diffTop.length()-1).toString()+");");
        StringBuilder diffBottom = new StringBuilder();
        vs.stream().skip(vs.size()-5).forEach(value -> diffBottom.append(value.key).append(","));
        System.out.println("select * from v_top_org_area  t where t.com_code in("+diffBottom.deleteCharAt(diffBottom.length()-1).toString()+");");
        System.out.println("相对值：");
        vs2.clear();
        vs2.addAll(vs.stream().limit(5).collect(Collectors.toList()));
        vs2.addAll(vs.stream().skip(vs.size()-5).collect(Collectors.toList()));
        System.out.println(vs2);
    }

    public class Value {
        String key;
        double value;
        double diff;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public double getDiff() {
            return diff;
        }

        public void setDiff(double diff) {
            this.diff = diff;
        }

        @Override public String toString() {
            return "Value{" +
                    "key='" + key + '\'' +
                    ", value=" + value +
                    ", diff=" + diff +
                    '}';
        }
    }

    public int getAsd() {
        return asd;
    }

    public void setAsd(int asd) {
        this.asd = asd;
    }

    public int getDsa() {
        return dsa;
    }

    public void setDsa(int dsa) {
        this.dsa = dsa;
    }
}
