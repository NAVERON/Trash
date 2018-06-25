


from ctypes import cdll
 
cur = cdll.LoadLibrary("./hello.so")
 
out = cur.foo(2, 3)
print(out)

hello = cur.get_double(4, 5, 8)
print(hello)



