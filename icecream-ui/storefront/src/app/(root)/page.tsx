import {
  request10FeaturedProducts,
  request12NewProducts,
  request10RecommendedProducts,
} from '@/repositories/frontier/products'
import { FeaturedCarousel } from './_components/feature-carousel'
import { ProductList, ViewAllProductsButton } from './_components/product-list'
import { DealCountdown } from './_components/deal-countdown'
import { IconBoxes } from '../_components/icon-boxes'

const Page = async () => {
  const [top10FeaturedProducts, top10NewProducts] = await Promise.all([
    request10FeaturedProducts(),
    request12NewProducts(),
  ])
  return (
    <>
      {top10FeaturedProducts.length > 0 ? <FeaturedCarousel data={top10FeaturedProducts} /> : null}
      <ProductList data={top10NewProducts} />
      <ViewAllProductsButton />
      <DealCountdown />
      <IconBoxes />
    </>
  )
}

export default Page
