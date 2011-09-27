package org.blackpanther.aestokenizer.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @author MACHIZAUD Andréa
 * @version 19/09/11
 */
public class DataProcessor {
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(DataProcessor.class.getCanonicalName());

    private StringTokenizer analyser;
    private String siteID = null;
    private Map<String, Collection<String>> data;

    public DataProcessor(final File dataFile) throws IOException {
        logger.info("Prepare to analyse " + dataFile.getCanonicalPath());

        final StringBuilder sb = new StringBuilder();

        final BufferedReader br = new BufferedReader(
                new FileReader(dataFile));
        {
            String line;
            while ((line = br.readLine()) != null)
                sb.append(line);
        }

        analyser = new StringTokenizer(sb.toString(), ";\n");

        data = new HashMap<String, Collection<String>>(6);
    }

    public String getSiteID() throws MalformedSiteIDException {
        ensureFetchSiteID();
        return siteID;
    }

    public Map<String, Collection<String>> getData() throws MalformedSiteIDException, MalformedContentException {
        ensureFetchData();
        return data;
    }

    private void ensureFetchSiteID() throws MalformedSiteIDException {
        if (siteID == null) {
            try {
                final String lblSiteID = analyser.nextToken();

                if (!lblSiteID.equals("Site ID"))
                    throw new MalformedSiteIDException("Site ID key not found, please check your file");

                siteID = analyser.nextToken();

                final Integer intSiteID = Integer.parseInt(siteID);
                if (intSiteID > 999999 || intSiteID < 0)
                    throw new MalformedSiteIDException("Invalidad Site ID, please enter only 6 number");

                logger.info("Site ID fetched : '" + siteID + "'");
            } catch (NoSuchElementException e) {
                throw new MalformedSiteIDException("Site ID fetch failed, please check your file");
            } catch (NumberFormatException e) {
                throw new MalformedSiteIDException("Site ID is not numeric, please check your file");
            }
        }
    }

    private void ensureFetchData() throws MalformedContentException, MalformedSiteIDException {
        //don't fetch the site ID by mistake
        ensureFetchSiteID();

        try {
            if (analyser != null) {
                //drain the rest of the file
                while (analyser.hasMoreTokens()) {
                    final String key = analyser.nextToken();
                    final String value = analyser.nextToken();

                    logger.info("Fetched data : '" + key + "' -> '" + value + "'");
                    put0(data, key, value);
                }

                //free the analyser, no more data available
                analyser = null;
            }
        } catch (NoSuchElementException e) {
            throw new MalformedContentException("Regularity of key-value broken, please check delimiter or any other error in your file");
        }
    }

    private void put0(Map<String, Collection<String>> map, String key, String value) {
        if (!map.containsKey(key)) {
            map.put(key, new LinkedList<String>());
        }
        map.get(key).add(value);
    }

    public abstract static class MalformedDataException extends Exception {
        protected MalformedDataException(String message) {
            super(message);
        }
    }

    public static class MalformedSiteIDException extends MalformedDataException {
        public MalformedSiteIDException(String message) {
            super(message);
        }
    }

    public static class MalformedContentException extends MalformedDataException {
        public MalformedContentException(String message) {
            super(message);
        }
    }
}
