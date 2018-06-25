#include <stdio.h>
#include <stdlib.h>
#include <dlfcn.h>

int main(void)
{
	printf("hello python warp!\n");
	void *this = dlopen("./hello.so", RTLD_LAZY);
	dlerror();
	int (*fun)() = dlsym(this, "foo");
	int (*other)() = dlsym(this, "get_double");
	int out1 = fun(2, 3);
	int out2 = other(4, 5, 9);
	
	printf("first out1 : %d \nsecond out2 : %d \n", out1, out2);
	dlclose(this);
	
	return 0;
}


