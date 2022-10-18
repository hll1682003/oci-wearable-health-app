package com.oracle.cloud.wearable.streaming.analytics;

import java.io.InputStream;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.function.Supplier;

import com.oracle.bmc.auth.BasicAuthenticationDetailsProvider;
import com.oracle.bmc.auth.InstancePrincipalsAuthenticationDetailsProvider;
import com.oracle.bmc.auth.ResourcePrincipalAuthenticationDetailsProvider;
import com.oracle.bmc.auth.SimpleAuthenticationDetailsProvider;
import com.oracle.bmc.auth.SimplePrivateKeySupplier;

public class GetProvider {

	static BasicAuthenticationDetailsProvider provider;
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static BasicAuthenticationDetailsProvider getProvider(String profile) {

//		BasicAuthenticationDetailsProvider provider = null;
		if(provider!=null)
			return provider;
		if ("LOCAL".equals(profile)) {
			Supplier<InputStream> privateKeySupplier = new SimplePrivateKeySupplier(Environment._PVT_KEY_FILE_PATH);
			provider = SimpleAuthenticationDetailsProvider.builder().tenantId(Environment._TENANT_ID)
					.userId(Environment._USER_ID).fingerprint(Environment._FINGER_PRINT)
					.privateKeySupplier((com.google.common.base.Supplier<InputStream>) privateKeySupplier).build();

		} else if(profile==null | "".equals(profile)) {
			try {
				System.out.println("Building Resource Principal for queue  ");
				provider = ResourcePrincipalAuthenticationDetailsProvider.builder().build();
			} catch (Exception e) {
				if (e.getCause() instanceof SocketTimeoutException || e.getCause() instanceof ConnectException) {
					System.out.println(
							"This sample only works when running on an OCI instance. Are you sure you�re running on an OCI instance? For more info see: https://docs.cloud.oracle.com/Content/Identity/Tasks/callingservicesfrominstances.htm");
					return provider;
				}
				throw e;
			}

		}
		return provider;
	}

}
