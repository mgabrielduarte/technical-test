# Technical test repository
API that offers a new feature to our customers showing similar products to the one they are currently seeing.

Once running, you can test that the api is working by executing the following command:

```
curl http://localhost:5000/actuator/health
```
You will receive the following response: 
{ "status": "UP"}


# Explanation on implementation

I tried a second implementation using parallelStream() but the times were, in general and under the resources of the pc used, better in the first implementation. 
Results are attached in the /main/resources/static/ directory.
```
            similarProductIds.parallelStream().forEach(item -> {
                try {
                    ProductDetail productDetail = getProductDetail(item);
                    similarProductsList.add(productDetail);
                } catch (Exception e) {
                    throw new NotFoundException(NOT_FOUND_MSG);
                }
            }); 
```
