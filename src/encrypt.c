#include <jni.h>
#include <jni_md.h>
#include <stdio.h>
#include <stdlib.h>
#include "Encrypt.h"

void encrypt (long *v, long *k){
/* TEA encryption algorithm */
unsigned long y = v[0], z=v[1], sum = 0;
unsigned long delta = 0x9e3779b9, n=32;

	while (n-- > 0){
		sum += delta;
		y += (z<<4) + k[0] ^ z + sum ^ (z>>5) + k[1];
		z += (y<<4) + k[2] ^ y + sum ^ (y>>5) + k[3];
	}

	v[0] = y;
	v[1] = z;
}



void decrypt (long *v, long *k){
/* TEA decryption routine */
unsigned long n=32, sum, y=v[0], z=v[1];
unsigned long delta=0x9e3779b9l;

	sum = delta<<5;
	while (n-- > 0){
		z -= (y<<4) + k[2] ^ y + sum ^ (y>>5) + k[3];
		y -= (z<<4) + k[0] ^ z + sum ^ (z>>5) + k[1];
		sum -= delta;
	}
	v[0] = y;
	v[1] = z;
}

JNIEXPORT jbyteArray JNICALL Java_Encrypt_encrypt
  (JNIEnv *env, jobject thisObj, jlongArray value, jlongArray key){

  	printf("encrypt\n");
  	jlong *v = (*env)->GetLongArrayElements(env, value, 0);
  	jlong *k = (*env)->GetLongArrayElements(env, key, 0);

  	jsize vSize = (*env)->GetArrayLength(env, value);

  	encrypt((long *) v, (long *) k);
	jlongArray res = (*env)->NewLongArray(env, vSize);
	(*env)->SetLongArrayRegion(env, res, 0, vSize, v);
	return res;
  }


JNIEXPORT jbyteArray JNICALL Java_Encrypt_decrypt
(JNIEnv *env, jobject thisObj, jlongArray value, jlongArray key){

  	jlong *v = (*env)->GetLongArrayElements(env, value, 0);
  	jlong *k = (*env)->GetLongArrayElements(env, key, 0);

  	jsize vSize = (*env)->GetArrayLength(env, value);
	
	decrypt((long *) v, (long *) k);

	jlongArray res = (*env)->NewLongArray(env, vSize);
	(*env)->SetLongArrayRegion(env, res, 0, vSize, v);
	printf("Decrypt\n");	
  	return res;
    
}

int main(int argc, char const *argv[])
{
	long key[4];
	long v[] = {0xFFFFFFFF, 0xFFFFFFFF};
	printf("Original values\n");
	printf("[%lX %lX]\n", v[0], v[1]);

	encrypt(v, key);
	printf("[%lX %lX]\n", v[0], v[1]);

	decrypt(v,key);
	printf("[%lX %lX]\n", v[0], v[1]);

    printf("Do it tomorrow\n");
    /* code */
    return 0;
}


