angular.module('market').controller('storeController', function ($scope, $http, $location, $localStorage) {
    const coreContextPath = 'http://localhost:5555/core/';
    const cartContextPath = 'http://localhost:5555/cart/';

    $scope.currentPage = 1;
    $scope.pageSize = 7;

    $scope.loadProducts = function (pageIndex = 1) {
        $http({
            method: 'GET',
            url: coreContextPath + 'api/v1/products',
            params: {
                title: $scope.productsfilter ? $scope.productsfilter.title : null,
                min_price: $scope.productsfilter ? $scope.productsfilter.maxPrice : null,
                max_price: $scope.productsfilter ? $scope.productsfilter.minPrice : null,
                page: pageIndex
            }
        }).then(function (response) {
            $scope.ProductsPage = response.data;

            let startPage = pageIndex - 2;
            let endPage = pageIndex + 2;

            if (endPage > $scope.ProductsPage.totalPages) {
                startPage -= (endPage - $scope.ProductsPage.totalPages);
                endPage = $scope.ProductsPage.totalPages;
            }

            if (startPage <= 0) {
                endPage += ((startPage - 1) * (-1));
                startPage = 1;
            }
            endPage = endPage > $scope.ProductsPage.totalPages ? $scope.ProductsPage.totalPages : endPage;
            $scope.PaginationArray = $scope.generatePagesIndexes(startPage, endPage);

        });
    }

    $scope.generatePagesIndexes = function (startPage, endPage) {
        let arr = [];
        for (let i = startPage; i <= endPage; i++) {
            arr.push(i);
        }
        return arr;
    }

    $scope.showProductInfo = function (productId) {
        $http.get(coreContextPath + 'api/v1/products/' + productId).then(function (response) {
            alert(response.data.title);
        });
    }

    $scope.deleteProductById = function (productId) {
        $http.delete(coreContextPath + 'api/v1/products/' + productId).then(function (response) {
            $scope.loadProducts();
        });
    }

    $scope.addToCart = function (productId) {
        $http.get(cartContextPath + 'api/v1/cart/' + $localStorage.gainanovMarketGuestCartId + '/add/' + productId).then(function (response) {
        });
    }

    $scope.loadProducts();

})